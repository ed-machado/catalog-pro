import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  http = inject(HttpClient);
private readonly apiUrl = 'http://backend:8080/category';

  readAllCategories(page: number, size: number): Observable<Page<Category>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<Page<Category>>(`${this.apiUrl}/read/all`, { params });
  }

  deleteCategory(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/delete/${id}`, { responseType: 'text' as 'json' });
  }

  createCategory(category: Category): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/create`, category, { responseType: 'text' as 'json' });
  }

  updateCategory(id: number, category: Category): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/update/${id}`, category, { responseType: 'text' as 'json' });
  }

  readCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/read/${id}`);
  }
}
