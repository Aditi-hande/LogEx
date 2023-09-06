import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';

import { AppService } from '../app.service';

@Component({
  selector: 'app-create-schedule',
  templateUrl: './create-schedule.component.html',
  styleUrls: ['./create-schedule.component.css']
})
export class CreateScheduleComponent implements OnInit {


  s:Schedule = new Schedule();
  hours:String="*";
  minutes:String="*";
  date:String="*";
  seconds:String ="0";
  month:String="*";
  dayOfWeek:String="*";
  cron:String;
  scheduleName: string;
  isScheduleEnabled:Boolean = false;
  isEmptyQueryList:Boolean = false;
  isEveryDayofWeek:boolean=false;
  isEveryMonth:boolean=false;
  dayList :String[]=["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"]
  bsValue = new Date();
  maxDate = new Date();
  minDate = new Date();
  time: Date = new Date();

  switchForEveryWeek(){
    console.log("Every Day of Week="+this.isEveryDayofWeek);
    if(this.isEveryDayofWeek === true){
      this.dayOfWeek="*";
      this.generateCron();
    }
    else{
      this.dayOfWeek = this.bsValue.getDay().toString();
      this.generateCron();
    }
  }

  switchForEveryMonth(){
    if(this.isEveryMonth === true){
      this.month="*";
      this.generateCron();
    }
    else{
      this.month = (this.bsValue.getMonth()+1).toString();
      this.generateCron();
    }
  }

  generateCron(){
    this.cron = this.seconds+" "+this.minutes+" "+this.hours+" "+this.date+" "+this.month+" "+this.dayOfWeek;
    //just for testing
    this.cron = "0/30 * * * * ?";
  }

  switchForDay(value:Date){
    this.bsValue = value;
    this.date = value.getDate().toString();
    this.month = (value.getMonth()+1).toString();
    this.dayOfWeek = value.getDay().toString();
    this.generateCron();

  }
  switchForTime(value:Date){
    this.time = value;
    this.hours = value.getHours().toString();
    this.minutes = value.getMinutes().toString();
    this.seconds = value.getSeconds().toString();
    this.generateCron();

  }

  ngOnInit(): void {
    this.generateCron();
  }

  
  constructor(private app:AppService , 
    private scheduleService:ScheduleService, 
    private router:Router){
    this.minDate.setDate(this.minDate.getDate() - 1);
    this.maxDate.setDate(this.maxDate.getDate() + 7); 
  }
  
  datetimeManip(){
    this.bsValue.setHours(this.time.getHours());
    this.bsValue.setMinutes(this.time.getMinutes());
    this.bsValue.setSeconds(this.time.getSeconds());
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    //bsValue is the final date time (use it to generate cron)
    this.datetimeManip();
    console.log(this.bsValue);
    console.log(this.cron);
    this.s.cron = this.cron;
    this.s.group= 'sample';
    this.scheduleService.createSchedule(this.s).subscribe(data=>{
    this.gotoScheduleList();
    },error=>console.log(error));
    
  }

  gotoScheduleList(){
    this.router.navigate(['/schedules']);

  }
  authenticated(){return this.app.authenticated};
}
