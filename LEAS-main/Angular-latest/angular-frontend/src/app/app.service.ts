import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  authenticated = false;
  baseURl='http://localhost:8080/user'
  constructor(private http: HttpClient,
    private router:Router) {
  }

  authenticate(credentials:any, callback:any) {
        // console.log("Inside authenticate"); 
        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});
        // console.log("headers="+JSON.stringify(headers))
        this.http.get(this.baseURl, {headers: headers,observe:  'response'}).subscribe((response:any) => {
            // console.log("response = "+JSON.stringify(response))
            // console.log("response name[]"+response.body.name)
            if (response.body['name']) {
                this.authenticated = true;
                const authorization = 'Basic ' + btoa(credentials.username + ':' + credentials.password)
                localStorage.setItem('currUser',authorization);
            } else {
                this.authenticated = false;
                localStorage.removeItem('currUser');
            }
            return callback && callback();
        },error=>{
            console.log("Login Failed. Incorret username or password");
            return callback && callback();
        });

    }
}
