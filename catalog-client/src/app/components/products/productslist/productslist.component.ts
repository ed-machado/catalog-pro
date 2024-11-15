import { Product } from './../../../models/product';
import { Component, inject, TemplateRef, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { ProductsdetailsComponent } from '../productsdetails/productsdetails.component';

@Component({
  selector: 'app-productslist',
  standalone: true,
  imports: [RouterLink, MdbModalModule, ProductsdetailsComponent],
  templateUrl: './productslist.component.html',
  styleUrls: ['./productslist.component.scss']
})
export class ProductslistComponent {


  list: Product[] = [];
  productEdit = new Product(0, "", "", 0);

  modalService = inject(MdbModalService);
  @ViewChild("modalProductDetail") modalProductDetail!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  constructor() {
    this.list.push(new Product(1, 'Bolsa', 'Resistente', 10))
    this.list.push(new Product(2, 'Casaco', 'Resistente', 14))
    this.list.push(new Product(3, 'Bota', 'Resistente', 12))

    let productNew = history.state.productNew;
    let productEdited = history.state.productEdited;

    if(productNew) {
      productNew.id = 555;
      this.list.push(productNew)
    }

    if(productEdited) {
      let index = this.list.findIndex(x => {return x.id == productEdited.id});
      this.list[index] = productEdited;
    }

  }

  deleteById(product: Product){

    Swal.fire({
      title: 'Are you sure ?',
      icon: 'warning',
      showConfirmButton: true,
      showDenyButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if(result.isConfirmed) {
        let index = this.list.findIndex(x => {return x.id == product.id});
      this.list.splice(index, 1);

      Swal.fire({
        title: 'Deleted with success',
        icon: 'success',
        confirmButtonText: 'Ok'
      })
      }
    })
  }

  new() {
    this.productEdit = new Product(0, "", "", 0);
    this.modalRef = this.modalService.open(this.modalProductDetail)
  }

  edit(product: Product) {
    this.productEdit = Object.assign({}, product);
    this.modalRef = this.modalService.open(this.modalProductDetail)
  }

  returnDetail(product: Product) {

    if(product.id > 0) {
      let index = this.list.findIndex(x => { return x.id == product.id });
      this.list[index] = product
    } else {
      product.id = 10000;
      this.list.push(product)
    }

    this.modalRef.close();
  }
}
