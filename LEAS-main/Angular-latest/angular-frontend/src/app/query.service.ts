import { HttpClient,HttpParams } from '@angular/common/http';
import {  Injectable } from '@angular/core';
import { logging } from 'protractor';
import { Observable } from 'rxjs';
import { LogLine } from './log-line';
import { Query } from './query';

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  private baseURL = "http://localhost:8080/queries";
  private baseURL2 = "http://localhost:8080";
  public count:String;
  
  constructor(private httpClient: HttpClient) { }

  getQueryList(): Observable<Query[]>{
    return this.httpClient.get<Query[]>(`${this.baseURL}` + '/getAll');
  }

  createQuery(q: Query):Observable<Object>{
    return this.httpClient.post(`${this.baseURL}` + '/create',q);
  }

  getQueryByName(queryName:String):Observable<Query>{
    return this.httpClient.get<Query>(`${this.baseURL}/${queryName}`);
  }

  updateQuery(queryName: String, query:Query):Observable<object>{
    return this.httpClient.put(`${this.baseURL}/${queryName}`, query);
  }

  runQuery(queryName:string):Observable<LogLine[]>{

    return this.httpClient.get<LogLine[]>(`${this.baseURL2}`+'/search',{params:new HttpParams().set("queryName",queryName)});
  }

  deleteQuery(queryName:String):Observable<object>{
    return this.httpClient.delete(`${this.baseURL}/${queryName}`);
  }

  countQuery():Observable<object>{
    return this.httpClient.get(`${this.baseURL}`+ '/count');
  }

  
}
