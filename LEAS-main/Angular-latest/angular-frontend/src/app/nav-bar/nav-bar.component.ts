import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { finalize } from "rxjs/operators";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(private app: AppService, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }
  logout() {
    this.http.post('logout', {}).pipe(finalize(() => {
        this.app.authenticated = false;
        localStorage.removeItem('currUser');
        this.router.navigateByUrl('/login');
    })).subscribe();
  }
  authenticated() {return this.app.authenticated; }

}
