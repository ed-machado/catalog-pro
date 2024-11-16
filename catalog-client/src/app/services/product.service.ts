import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/product';

  constructor() { }

  readAllProduct(page: number, size: number): Observable<Page<Product>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<Product>>(`${this.apiUrl}/read/all`, { params });
  }

  deleteProduct(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/delete/${id}`, {responseType: 'text' as 'json'});
  }

  createProduct(product: Product): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/create`, product, {responseType: 'text' as 'json'});
  }

  updateProduct(id: number, product: Product): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/update/${id}`, product, {responseType: 'text' as 'json'});
  }

  readProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/read/${id}`);
  }

  readProductByCategoryName(categoryName: string): Observable<Product[]> {
    const params = new HttpParams().set('categoryName', categoryName);
    return this.http.get<Product[]>(`${this.apiUrl}/read/byCategoryName`, { params });
  }
}

