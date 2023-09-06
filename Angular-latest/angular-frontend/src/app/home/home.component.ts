import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MaskService } from '../mask.service';
import { QueryService } from '../query.service';
import { ScheduleService } from '../schedule.service';
import { TargetService } from '../target.service';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  queryCount:BigIntToLocaleStringOptions;
  maskCount:BigIntToLocaleStringOptions;
  targetCount:BigIntToLocaleStringOptions;
  scheduleCount:BigIntToLocaleStringOptions;


  constructor(
    private app:AppService,
    private queryService:QueryService,
    private maskService:MaskService, 
    private targetService : TargetService, 
    private router:Router, 
    private scheduleService: ScheduleService) {
    this.queryService.countQuery().subscribe(data => {this.queryCount = data;}) //async calls
    this.maskService.countMask().subscribe(data => {this.maskCount = data;}) //async calls
    this.targetService.countTarget().subscribe(data=> {this.targetCount=data;}) //async calls
    this.scheduleService.countSchedule().subscribe(data=> {this.scheduleCount=data;}) //async calls
    
    }

  ngOnInit(): void {
    
  }
  gotoQueryList(){
    this.router.navigate(['/queries']);
  
  }
  
  gotoScheduleList(){
    this.router.navigate(['/schedules']);
  
  }

  gotoMaskList(){
    this.router.navigate(['masks']);
  }

  gotoTargetList(){
    this.router.navigate(['targets']);
  }
  authenticated(){return this.app.authenticated
  };

}
