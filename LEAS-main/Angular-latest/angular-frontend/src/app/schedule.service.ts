import { HttpClient,HttpHeaders } from '@angular/common/http';
import {  Injectable } from '@angular/core';
import { logging } from 'protractor';
import { Observable } from 'rxjs';
import { threadId } from 'worker_threads';
import { LogLine } from './log-line';
import { Query } from './query';
import { Schedule } from './schedule';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private baseURL = "http://localhost:8080/schedules";
  private baseURL2 = "http://localhost:8080/groups";
  public count:String;

  constructor(private httpClient: HttpClient) { }

  getScheduleList(): Observable<Schedule[]>{
    return this.httpClient.get<Schedule[]>(`${this.baseURL}` + '/getAll');
  }

  createSchedule(s: Schedule):Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`+'/create',s)
  }

  getScheduleByName(scheduleName:String):Observable<Schedule>{
    return this.httpClient.get<Schedule>(`${this.baseURL}/${scheduleName}`);
  }

  updateSchedule(scheduleName: String, schedule:Schedule):Observable<object>{
    return this.httpClient.put(`${this.baseURL}/${scheduleName}`, schedule);
  }

  runSchedule(s:Schedule):any{
    // console.log("Running Schedule "+JSON.stringify(s));
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',

      })
    };

    const jobDescriptor:JD=new JD();
    const trigger:Trigger = new Trigger();
    trigger.name=s.name;
    trigger.group=s.group;
    trigger.cron = s.cron;
    jobDescriptor.name=s.name;
    jobDescriptor.group=s.group;
    jobDescriptor.query=s.queryList;
    jobDescriptor.triggers.push(trigger);
    // console.log(jobDescriptor);
    // console.log(params.get('descriptor'));
    this.httpClient.post<object>(`${this.baseURL2}/${s.group}`+'/jobs',jobDescriptor,httpOptions).subscribe(
      response => console.log(JSON.stringify(response)),
      error => console.log(error)
    );
  }

  // pauseSchedule(name:string):Observable<Boolean>{

  // }
  // resumeSchedule(name:String):Observable<Boolean>{

  // }
  checkIfScheduleRunning(s:Schedule):Observable<Boolean>{
    return this.httpClient.get<Boolean>(`${this.baseURL2}/${s.group}`+'/jobs/isRunning/'+ `${s.name}`)
  }

  deleteScheduleRunning(s:Schedule):Observable<object>{
    console.log("Deleting "+s.group+" "+s.name);
    return this.httpClient.delete(`${this.baseURL2}/${s.group}`+'/jobs/'+ `${s.name}`)
  }

  deleteSchedule(scheduleName:String):Observable<object>{
    return this.httpClient.delete(`${this.baseURL}/${scheduleName}`);
  }
  countSchedule():Observable<object>{
    // console.log("Counting Schedule")
    return this.httpClient.get(`${this.baseURL}`+ '/count');
  }
}

class JD{
  name:String;
  group:String;
  query:String[];
  triggers:Trigger[]=[];
}
class Trigger{
  name:String;
  group:String;
  cron:String;
}