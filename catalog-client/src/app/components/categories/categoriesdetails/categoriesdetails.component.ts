import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { FormsModule } from '@angular/forms';
import { Category } from '../../../models/category';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoryService } from '../../../services/categories.service';

@Component({
  selector: 'app-categoriesdetails',
  standalone: true,
  imports: [MdbFormsModule, FormsModule],
  templateUrl: './categoriesdetails.component.html',
  styleUrls: ['./categoriesdetails.component.scss']
})
export class CategoriesdetailsComponent {
  @Input("category") category: Category = new Category(0, "", "");
  @Output("return") return = new EventEmitter<any>();
  router = inject(ActivatedRoute);
  router2 = inject(Router);

  categoryService = inject(CategoryService);

  save(): void {
    if (this.category.id > 0) {
      this.categoryService.updateCategory(this.category.id, this.category).subscribe({
        next: (response) => {
          Swal.fire({ title: response, icon: 'success', confirmButtonText: 'Ok' });
          this.return.emit(this.category);
        },
        error: (err) => {
          Swal.fire({ title: err, text: 'Error updating category.', icon: 'error', confirmButtonText: 'Ok' });
        },
      });
    } else {
      this.categoryService.createCategory(this.category).subscribe({
        next: (response) => {
          Swal.fire({ title: response, icon: 'success', confirmButtonText: 'Ok' });
          this.return.emit(this.category);
        },
        error: (err) => {
          Swal.fire({ title: err, text: 'Error creating category.', icon: 'error', confirmButtonText: 'Ok' });
        },
      });
    }
  }

  cancel(): void {
    this.return.emit();
  }
}

