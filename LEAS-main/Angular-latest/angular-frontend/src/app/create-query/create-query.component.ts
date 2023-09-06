import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
import { ElasticSearchIndexServiceService } from '../elastic-search-index-service.service';
import { Mask } from '../mask';
import { MaskService } from '../mask.service';
import { Query } from '../query';
import { QueryService } from '../query.service';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import { Target } from '../target';
import { TargetService } from '../target.service';

@Component({
  selector: 'app-create-query',
  templateUrl: './create-query.component.html',
  styleUrls: ['./create-query.component.css']
})
export class CreateQueryComponent implements OnInit {

  q: Query = new Query();
  productopt:String[]=["CS","MSC"];
  maskopt:Mask[];
  scheduleopt: Schedule[];
  targetopt:Target[];
  indexopt:String[];


  constructor(
    private app:AppService,
    private queryService: QueryService,
    private router:Router, 
    private maskService: MaskService,
    private scheduleService: ScheduleService,
    private targetService:TargetService,
    private elasticSearchIndexService: ElasticSearchIndexServiceService) { }

  ngOnInit(): void { 
    // console.log("init")
    this.getMasks();
    this.getSchedules();
    this.getTargets();
    this.getIndices();
  }

  saveQuery(){
    this.queryService.createQuery(this.q).subscribe(data =>{
      console.log(data);
      this.gotoQueryList();
    },error=>console.log(error));
}

getMasks(){
  this.maskService.getMaskList().subscribe(data=>{
    this.maskopt=data;
    // console.log('getting mask');
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
    // console.log('getting mask');
  },error =>console.log(error));

}

getIndices(){
  this.elasticSearchIndexService.getIndexList().subscribe(data=>{
    this.indexopt=data;
    // console.log('getting mask');
  },error =>console.log(error));

}
gotoQueryList(){
  this.router.navigate(['/queries']);

}

onSubmit(){
      console.log(this.q);
      this.saveQuery();
  }
  authenticated(){return this.app.authenticated};

}
