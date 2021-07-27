import {Component, OnInit} from '@angular/core';
import {Product} from "../model/product";
import {ProductService} from '../model/product.service';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {

  public product = new Product(-1, "", 0);
  public isError: boolean = false;

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params.id == 'new') {
        this.product = new Product(-1, "", 0);
        console.log(params.id)
        return;
      }
      this.productService.findById(params.id)
        .then(result => {
          console.log(params.id)
          this.isError = false;
          this.product = result;
        })
        .catch(error => {
          console.error(error);
          this.isError = true;
        })
    })
  }

  public save() {
    this.productService.save(this.product)
      .then(() => {
        this.isError = false;
        this.router.navigateByUrl('/product');
      })
      .catch(error => {
        console.error(error);
        this.isError = true;
      })
  }

}
