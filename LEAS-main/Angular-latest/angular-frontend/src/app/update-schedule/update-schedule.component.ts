import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from '../app.service';
import { Query } from '../query';
import { QueryService } from '../query.service';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import { Trigger } from '../trigger';

@Component({
  selector: 'app-update-schedule',
  templateUrl: './update-schedule.component.html',
  styleUrls: ['./update-schedule.component.css']
})
export class UpdateScheduleComponent implements OnInit {

  s:Schedule  = new Schedule();
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
  isEveryMinute:boolean=false;
  isEveryHour:boolean=false;
  isEveryMonth:boolean=false;
  dayList :String[]=["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"]

  bsValue = new Date();
  maxDate = new Date();
  minDate = new Date();
  time: Date = new Date();
  // freqopt:String[]=["Startup","Hourly","Daily","Weekly","Monthly","Yearly"];

  constructor(
    private app:AppService,
    private queryService: QueryService,
              private route: ActivatedRoute,
              private router: Router,
              private scheduleService: ScheduleService ) { }

  switchForEveryWeek(){
    if(this.isEveryDayofWeek === true){
      this.dayOfWeek="*";
      this.generateCron();
    }
    else{
      this.dayOfWeek = this.bsValue.getDay().toString();
      this.generateCron();
    }
  }
  switchForEveryMinute(){
    if(this.isEveryMinute === true){
      this.minutes="*";
      this.generateCron();
    }
    else{
      this.minutes = this.bsValue.getMinutes().toString();
      this.generateCron();
    }
  }
  switchForEveryHour(){
    if(this.isEveryHour === true){
      this.hours="*";
      this.generateCron();
    }
    else{
      this.hours = this.bsValue.getHours().toString();
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
    this.cron = this.seconds+" "+this.minutes+" "+this.hours+" "+this.date+" "+this.month+" "+"? *";
    //just for testing
    // this.cron = "30 * * * * ? *";
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
    this.scheduleName=this.route.snapshot.params['scheduleName'];
    this.scheduleService.getScheduleByName(this.scheduleName).subscribe(data=>{
      this.s=data;
      this.isEmptyQueryList = this.s.queryList && this.s.queryList.length>0;
      this.scheduleService.checkIfScheduleRunning(this.s).subscribe(data=>{
        this.isScheduleEnabled = data;
      }, error=> console.error(error));
    },error=> console.error(error));
  }

  gotoList(){
    this.router.navigate(['/schedules']);
  
  }

  onCancel(){
    this.gotoList();
  }

  datetimeManip(){
    this.bsValue.setHours(this.time.getHours());
    this.bsValue.setMinutes(this.time.getMinutes());
    this.bsValue.setSeconds(this.time.getSeconds());
  }
  onSubmit() {
    //bsValue is the final date time (use it to generate cron)
    this.datetimeManip();
    this.s.cron = this.cron;
    this.scheduleService.updateSchedule(this.scheduleName,this.s).subscribe(data=>{
      if(this.isScheduleEnabled==true)
        this.scheduleService.runSchedule(this.s);
      else{
          this.scheduleService.deleteScheduleRunning(this.s).subscribe((data)=>{
            console.log(data);
          });
          console.log("Deleting a running schedule");
        }
      this.gotoList();
    },error=> console.log(error));
  }
  authenticated() {return this.app.authenticated; }

}
