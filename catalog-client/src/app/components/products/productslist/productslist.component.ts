import { ProductService } from './../../../services/product.service';
import { Product } from './../../../models/product';
import { Category } from './../../../models/category';
import { Component, inject, OnInit, TemplateRef, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { ProductsdetailsComponent } from '../productsdetails/productsdetails.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productslist',
  standalone: true,
  imports: [CommonModule, MdbModalModule, ProductsdetailsComponent],
  templateUrl: './productslist.component.html',
  styleUrls: ['./productslist.component.scss']
})
export class ProductslistComponent implements OnInit {
  products: Product[] = [];
  totalPages = 0;
  currentPage = 0;
  productEdit = new Product(0, "", "", 0, new Category(0, "", ""));

  modalService = inject(MdbModalService);
  @ViewChild("modalProductDetail") modalProductDetail!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  productService = inject(ProductService);
  isDarkMode: boolean = false;

  constructor() {
    const productNew = history.state.productNew;
    const productEdited = history.state.productEdited;

    if (productNew) {
      productNew.id = 555;
      this.products.push(productNew);
    }

    if (productEdited) {
      const index = this.products.findIndex((x) => x.id === productEdited.id);
      this.products[index] = productEdited;
    }
  }

  trackByIndex(index: number, product: Product): number {
    return product.id;
  }

  ngOnInit(): void {
    this.readAllProduct(0, 10);
  }

  readAllProduct(page: number, size: number): void {
    this.productService.readAllProduct(page, size).subscribe({
      next: (response) => {
        this.products = response.content; // Lista de produtos
        this.totalPages = response.totalPages; // Total de páginas
        this.currentPage = response.number; // Página atual
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

  deleteProduct(product: Product): void {
    Swal.fire({
      title: 'Are you sure?',
      icon: 'warning',
      showConfirmButton: true,
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.productService.deleteProduct(product.id).subscribe({
          next: (response) => {
            Swal.fire({
              title: response,
              icon: 'success',
              confirmButtonText: 'Ok',
            });
            this.readAllProduct(0, 10);
            console.log(response);
          },
          error: (err) => {
            Swal.fire({
              title: 'Error deleting product',
              text: 'An error occurred while deleting the product.',
              icon: 'error',
              confirmButtonText: 'Ok',
            });
            console.error('Error deleting product', err);
          },
        });
      }
    });
  }

  new() {
    this.productEdit = new Product(0, "", "", 0, new Category(0, "", "")); // Categoria padrão
    this.modalRef = this.modalService.open(this.modalProductDetail);
  }

  edit(product: Product) {
    this.productEdit = Object.assign({}, product);
    this.modalRef = this.modalService.open(this.modalProductDetail);
  }

  returnDetail() {
    this.readAllProduct(0, 10);
    this.modalRef.close();
  }
}
