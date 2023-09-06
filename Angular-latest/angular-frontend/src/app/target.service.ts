import { HttpClient } from '@angular/common/http';
import {  Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Target } from './target';

@Injectable({
  providedIn: 'root'
})
export class TargetService {

  private baseURL = "http://localhost:8080/target";
  
  constructor(private httpClient: HttpClient) { }

  getTargetList(): Observable<Target[]>{
    return this.httpClient.get<Target[]>(`${this.baseURL}` + '/getAll');
  }

  createTarget(t: Target):Observable<Object>{
    return this.httpClient.post(`${this.baseURL}` + '/create',t);
  }

  getTargetByName(targetName:String):Observable<Target>{
    return this.httpClient.get<Target>(`${this.baseURL}/${targetName}`);
  }

  updateTarget(targetName: String, target:Target):Observable<object>{
    return this.httpClient.put(`${this.baseURL}/${targetName}`, target);
  }

  deleteTarget(targetName:String):Observable<object>{
    return this.httpClient.delete(`${this.baseURL}/${targetName}`);
  }
  countTarget():Observable<object>{
    return this.httpClient.get(`${this.baseURL}` + '/count');
  }

}
