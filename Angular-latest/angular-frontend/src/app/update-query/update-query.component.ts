import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Mask } from '../mask';
import {Target} from '../target';
import { MaskService } from '../mask.service';
import { Query } from '../query';
import { QueryService } from '../query.service';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import { TargetService } from '../target.service';
import { ElasticSearchIndexServiceService } from '../elastic-search-index-service.service';
import { AppService } from '../app.service';

@Component({
  selector: 'app-update-query',
  templateUrl: './update-query.component.html',
  styleUrls: ['./update-query.component.css']
})
export class UpdateQueryComponent implements OnInit {

  q: Query = new Query();
  queryName: string;
   productopt:String[]=["CS","MSC"];;
   maskopt:Mask[];
   scheduleopt: Schedule[];
   targetopt:Target[];
   indexopt:String[];
   

  constructor(
    private app:AppService,
    private queryService: QueryService,
              private route: ActivatedRoute,
              private router: Router,
              private maskService: MaskService,
              private scheduleService : ScheduleService,
              private targetService:TargetService,
              private elasticSearchIndexService:ElasticSearchIndexServiceService ) { }

  ngOnInit(): void {
    this.getMasks();
    this.getSchedules();
    this.getTargets();
    this.getIndices();
    this.queryName=this.route.snapshot.params['queryName'];
    this.queryService.getQueryByName(this.queryName).subscribe(data=>{
      this.q=data;
    },error=> console.log(error));
  }

  gotoQueryList(){
    this.router.navigate(['/queries']);
  
  }
  getMasks(){
    this.maskService.getMaskList().subscribe(data=>{
      this.maskopt=data;
    },error =>console.log(error));
  
  }
  getSchedules(){
    this.scheduleService.getScheduleList().subscribe(data=>{
      this.scheduleopt=data;
    },error =>console.log(error));
  
  }

  getTargets(){
    this.targetService.getTargetList().subscribe(data=>{
      this.targetopt=data;
    },error =>console.log(error));
  
  }

  getIndices(){
    this.elasticSearchIndexService.getIndexList().subscribe(data=>{
      this.indexopt=data;
    },error =>console.log(error));
  
  }
  onCancel(){
      this.gotoQueryList();
  }

  onSubmit(){
    this.queryService.updateQuery(this.queryName,this.q).subscribe(data=>{
      this.gotoQueryList();
    },error=> console.log(error));
  }
  authenticated() {return this.app.authenticated; }


}
