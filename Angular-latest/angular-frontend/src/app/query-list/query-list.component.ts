import { Component, OnInit } from '@angular/core';
import { Query } from '../query';
import { QueryService } from '../query.service';
import { Router, RouterModule } from '@angular/router';
import {ScheduleService} from '../schedule.service';
import { AppService } from '../app.service';

@Component({
  selector: 'app-query-list',
  templateUrl: './query-list.component.html',
  styleUrls: ['./query-list.component.css']
})

export class QueryListComponent implements OnInit {

  queries: Query[];
  scheduleCount:BigIntToLocaleStringOptions;
  
  constructor(
    private app:AppService,
    private queryService: QueryService,private scheduleService:ScheduleService , private router: Router) { } //injected query service 

  ngOnInit(): void {
    this.getQueries();
    this.scheduleService.countSchedule().subscribe(data=> {this.scheduleCount=data;})
  }

  private getQueries(){
    this.queryService.getQueryList().subscribe(data => {this.queries = data;}) //async calls
  }
  
  gotoCreateQuery(){
      this.router.navigate(['/create-query']);
  }
  updateQuery(queryName: String){
    this.router.navigate(['update-query',queryName]);
  }
  runQueryOnce(queryName:String){
    this.router.navigate(['query-result',queryName]);
  }
  deleteQuery(queryName:String){
    this.queryService.deleteQuery(queryName).subscribe(data =>{
      this.getQueries();
    })
  }
  authenticated() {return this.app.authenticated; }

}
