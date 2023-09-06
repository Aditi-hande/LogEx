import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Schedule } from '../schedule';
import { ScheduleService } from '../schedule.service';
import {QueryService} from '../query.service';
import { AppService } from '../app.service';

@Component({
  selector: 'app-schedule-list',
  templateUrl: './schedule-list.component.html',
  styleUrls: ['./schedule-list.component.css']
})
export class ScheduleListComponent implements OnInit {

  schedules: Schedule[]=[];
  
  constructor(
    private app:AppService,
    private queryService:QueryService, private scheduleService: ScheduleService, private router: Router) { } //injected service 

  ngOnInit(): void {
    this.getschedules();
  }
  private getschedules(){
    this.scheduleService.getScheduleList().subscribe(data => {this.schedules = data;}) //async calls
  }

  gotoCreateSchedule(){
    this.router.navigate(['/create-schedule']);
  }


  updateSchedule(scheduleName: String){
    this.router.navigate(['update-schedule',scheduleName]);
  }

  runSchedule(scheduleName:String){
    // this.router.navigate(['scheduleresult',scheduleName]);
  }

  deleteSchedule(scheduleName:String){
    this.scheduleService.deleteSchedule(scheduleName).subscribe(data =>{
      this.getschedules();
    },error=>console.log(error));
  }

  gotoQueryList(){
    this.router.navigate(['/queries']);
  }
  authenticated() {return this.app.authenticated; }

}
