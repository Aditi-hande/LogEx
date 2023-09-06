import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { version } from 'process';
import { AppService } from '../app.service';
import { LogLine } from '../log-line';
import { QueryService } from '../query.service';

@Component({
  selector: 'app-query-result',
  templateUrl: './query-result.component.html',
  styleUrls: ['./query-result.component.css']
})
export class QueryResultComponent implements OnInit {

  queryName: string;
  queryResult: LogLine[]; // this is where response array should be stored

  constructor(
    private app:AppService,
    private queryService: QueryService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.queryName=this.route.snapshot.params['queryName'];
    this.queryService.runQuery(this.queryName).subscribe(data=>{
      this.queryResult =data;
    },error=> console.log(error));
    

  }

  gotoQueryList(){
    this.router.navigate(['/queries']);
  
  }
  authenticated() {return this.app.authenticated; }

}
