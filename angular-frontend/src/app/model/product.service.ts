import {Injectable} from '@angular/core';
import {Product} from "./product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private identity: number = 6;

  private products: { [key: number]: Product } = {
    1: new Product(1, 'Apples', 2.55),
    2: new Product(2, 'Pears', 3.00),
    3: new Product(3, 'Strawberry', 7.00),
    4: new Product(4, 'Oranges', 3.50),
    5: new Product(5, 'Kiwi', 4.20)
  }

  constructor(public http: HttpClient) {
  }

  public findAll() {
    return this.http.get<Product[]>('/api/v1/product/all').toPromise();
  }

  public findById(id: number) {
    return this.http.get<Product>(`/api/v1/product/${id}`).toPromise();
  }

  public save(product: Product) {
    return this.http.put('/api/v1/product', product).toPromise();
  }

  public delete(id: number) {
    return this.http.delete(`/api/v1/product/${id}`).toPromise();
  }
}
