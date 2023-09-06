import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  inCorrect:Boolean=true
  credentials = {username: '', password: ''};
  constructor(private app: AppService, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    if(localStorage.getItem('currUser')!=null){
        this.router.navigateByUrl('home');
    }
  }

  login() {
    this.app.authenticate(this.credentials, () => {
        console.log("Login successfull. Routing to home")
        if (this.app.authenticated){
          this.router.navigateByUrl('home');
        }
        else{
          this.inCorrect=false;
        }
    });
  }

}
