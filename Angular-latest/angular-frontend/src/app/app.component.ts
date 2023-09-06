import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-frontend';
  constructor(private app: AppService, private http: HttpClient, private router:Router){
    this.app.authenticate(undefined, undefined);
    }
  }
