import { Component, EventEmitter, inject, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/categories.service';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product';
import { CategoriesdetailsComponent } from '../categoriesdetails/categoriesdetails.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categorieslist',
  standalone: true,
  imports: [CommonModule, MdbModalModule, CategoriesdetailsComponent],
  templateUrl: './categorieslist.component.html',
  styleUrls: ['./categorieslist.component.scss']
})
export class CategorieslistComponent implements OnInit {
  categories: Category[] = [];
  products: Product[] = [];
  totalPages = 0;
  currentPage = 0;
  categoryEdit = new Category(0, "", "");

  @Input("hideButtons") hideButtons: boolean = false;
  @Output("return") return = new EventEmitter<any>();

  modalService = inject(MdbModalService);
  @ViewChild("modalCategoryDetail") modalCategoryDetail!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  categoryService = inject(CategoryService);
  productService = inject(ProductService);

  constructor() {
    let categoryNew = history.state.categoryNew;
    let categoryEdited = history.state.categoryEdited;

    if (categoryNew) {
      categoryNew.id = 555;
      this.categories.push(categoryNew);
    }

    if (categoryEdited) {
      let index = this.categories.findIndex(x => x.id === categoryEdited.id);
      this.categories[index] = categoryEdited;
    }
  }

  ngOnInit(): void {
    this.readAllCategories(0, 10);
  }

  trackById(index: number, category: Category): number {
    return category.id;
  }

  readAllCategories(page: number, size: number): void {
    this.categoryService.readAllCategories(page, size).subscribe({
      next: (response) => {
        this.categories = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      },
      error: (err) => {
        Swal.fire({
          title: 'Error loading categories',
          text: 'An error occurred while loading categories.',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
        console.error('Error loading categories', err);
      },
    });
  }

  readProductsByCategory(categoryName: string): void {
    this.productService.readProductByCategoryName(categoryName).subscribe({
      next: (products) => {
        this.products = products;
        console.log('Products:', this.products);
      },
      error: (err) => {
        Swal.fire({
          title: 'Error loading products',
          text: 'An error occurred while loading products.',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
        console.error('Error loading products', err);
      },
    });
  }

  deleteCategory(category: Category): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoryService.deleteCategory(category.id).subscribe({
          next: (response) => {
            Swal.fire({
              title: response,
              icon: 'success',
              confirmButtonText: 'Ok',
            });
            this.readAllCategories(0, 10);
          },
          error: (err) => {
            Swal.fire({
              title: 'Error deleting category',
              text: 'An error occurred while deleting the category.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
            console.error('Error deleting category', err);
          },
        });
      }
    });
  }

  new(): void {
    this.categoryEdit = new Category(0, "", "");
    this.modalRef = this.modalService.open(this.modalCategoryDetail);
  }

  edit(category: Category): void {
    this.categoryEdit = Object.assign({}, category);
    this.modalRef = this.modalService.open(this.modalCategoryDetail);
  }

  returnDetail(): void {
    this.readAllCategories(0, 10);
    this.modalRef.close();
  }

  select(category: Category) {
    this.return.emit(category);
  }
}
