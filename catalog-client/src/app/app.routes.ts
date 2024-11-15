import { Routes } from '@angular/router';
import { LoginComponent } from './components/layout/login/login.component';
import { PrincipalComponent } from './components/layout/principal/principal.component';
import { ProductslistComponent } from './components/products/productslist/productslist.component';
import { ProductsdetailsComponent } from './components/products/productsdetails/productsdetails.component';
import { CategorieslistComponent } from './components/categories/categorieslist/categorieslist.component';
import { CategoriesdetailsComponent } from './components/categories/categoriesdetails/categoriesdetails.component';

export const routes: Routes = [
  {path: "", redirectTo: "login", pathMatch: 'full'},
  {path :"login", component: LoginComponent},
  {path: "admin", component: PrincipalComponent, children: [
    {path: "products", component: ProductslistComponent},
    {path: "products/new", component: ProductsdetailsComponent},
    {path: "products/edit/:id", component: ProductsdetailsComponent},
    {path: "categories", component: CategorieslistComponent},
    {path: "categories/new", component: CategoriesdetailsComponent},
    {path: "categories/edit/:id", component: CategoriesdetailsComponent}
  ]}
];
