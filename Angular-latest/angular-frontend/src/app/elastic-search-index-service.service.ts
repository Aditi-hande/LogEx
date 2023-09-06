import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ElasticSearchIndexServiceService {


  private baseURL = "http://localhost:8080/getAllIndices";

  constructor(private httpClient: HttpClient) { }
  
  getIndexList(): Observable<String[]>{
    return this.httpClient.get<String[]>(`${this.baseURL}`);
  }

}
