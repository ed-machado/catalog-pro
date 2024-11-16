import { Component, EventEmitter, inject, Input, Output, TemplateRef, ViewChild } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms'
import { FormsModule } from '@angular/forms';
import { Product } from '../../../models/product';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Category } from './../../../models/category';
import { ProductService } from '../../../services/product.service';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { CategorieslistComponent } from '../../categories/categorieslist/categorieslist.component';

@Component({
  selector: 'app-productsdetails',
  standalone: true,
  imports: [MdbFormsModule, FormsModule, CategorieslistComponent],
  templateUrl: './productsdetails.component.html',
  styleUrl: './productsdetails.component.scss'
})
export class ProductsdetailsComponent {

  @Input("product") product: Product = new Product(0, "", "", 0, new Category(0, "", ""));
  @Output("return") return = new EventEmitter<any>();
  router = inject(ActivatedRoute)
  router2 = inject(Router)

  modalService = inject(MdbModalService);
  @ViewChild("modalCategoriesRead") modalCategoriesRead!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  productService = inject(ProductService);

  constructor() {
    let id = this.router.snapshot.params['id'];
    if(id > 0) {
      this.loadProductById(id);
    }
  }

  loadProductById(id: number) {
    this.productService.readProductById(id).subscribe({
      next: (product) => this.product = product,
      error: (err) => {
        Swal.fire({
          title: err,
          text: 'An error occurred while deleting the product.',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      },
    });
  }

  save() {

    if(this.product.id > 0) {
      this.productService.updateProduct(this.product.id, this.product).subscribe({
        next: (response) => {
          Swal.fire({
            title: response,
            icon: 'success',
            confirmButtonText: 'Ok'
          })
          this.router2.navigate(['admin/products'], { state: { productEdited: this.product } });
          this.return.emit(this.product);
        },
        error: (err) => {
          Swal.fire({
            title: err,
            text: 'An error occurred while deleting the product.',
            icon: 'error',
            confirmButtonText: 'Ok',
          })
          this.return.emit(this.product);
        },
      });
    } else {

      this.productService.createProduct(this.product).subscribe({
        next: (response) => {
          Swal.fire({
            title: response,
            icon: 'success',
            confirmButtonText: 'Ok'
          })
          this.router2.navigate(['admin/products'], { state: { productEdited: this.product } });
          this.return.emit(this.product);
        },
        error: (err) => {
          Swal.fire({
            title: err,
            text: 'An error occurred while deleting the product.',
            icon: 'error',
            confirmButtonText: 'Ok',
          })
          this.return.emit(this.product);
        },
      });
    }
  }


  readCategory() {
    this.modalRef = this.modalService.open(this.modalCategoriesRead, {modalClass: 'modal-lg'})
  }

  returnCategory(category: Category) {
    this.product.category = category;
    this.modalRef.close();
  }


}
