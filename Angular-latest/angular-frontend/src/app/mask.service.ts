import { HttpClient } from '@angular/common/http';
import {  Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Mask } from './mask';

@Injectable({
  providedIn: 'root'
})
export class MaskService {

  private baseURL = "http://localhost:8080/mask";
  
  constructor(private httpClient: HttpClient) { }

  getMaskList(): Observable<Mask[]>{
    return this.httpClient.get<Mask[]>(`${this.baseURL}` + '/getAll');
  }

  createMask(m: Mask):Observable<Object>{
    return this.httpClient.post(`${this.baseURL}` + '/create',m);
  }

  getMaskByName(maskName:String):Observable<Mask>{
    return this.httpClient.get<Mask>(`${this.baseURL}/${maskName}`);
  }

  updateMask(maskName: String, mask:Mask):Observable<object>{
    return this.httpClient.put(`${this.baseURL}/${maskName}`, mask);
  }

  deleteMask(maskName:String):Observable<object>{
    return this.httpClient.delete(`${this.baseURL}/${maskName}`);
  }
  countMask():Observable<object>{
    return this.httpClient.get(`${this.baseURL}` + '/count');
  }

}
