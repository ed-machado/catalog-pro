import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms'
import { FormsModule } from '@angular/forms';
import { Product } from '../../../models/product';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-productsdetails',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './productsdetails.component.html',
  styleUrl: './productsdetails.component.scss'
})
export class ProductsdetailsComponent {

  @Input("product") product: Product = new Product(0, "", "", 0);
  @Output("return") return = new EventEmitter<any>();
  router = inject(ActivatedRoute)
  router2 = inject(Router)

  constructor() {
    let id = this.router.snapshot.params['id'];
    if(id > 0) {
      this.findById(id);
    }
  }

  findById(id: number) {
    let productRetorned: Product = new Product(id, "", "", 10);
    this.product = productRetorned;
  }

  save() {

    if(this.product.id > 0) {
      Swal.fire({
        title: 'Edit with success',
        icon: 'success',
        confirmButtonText: 'Ok'
      })
      this.router2.navigate(['admin/products'], { state: { productEdited: this.product } });
    } else {
      Swal.fire({
        title: 'Saved with success',
        icon: 'success',
        confirmButtonText: 'Ok'
      })
      this.router2.navigate(['admin/products'], { state: { productNew: this.product } })
    }

    this.return.emit(this.product);

  }
}
