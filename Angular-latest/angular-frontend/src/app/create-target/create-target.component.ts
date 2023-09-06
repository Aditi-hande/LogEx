import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Target } from '../target';
import { TargetService } from '../target.service';
import {AppService} from '../app.service';
@Component({
  selector: 'app-create-target',
  templateUrl: './create-target.component.html',
  styleUrls: ['./create-target.component.css']
})
export class CreateTargetComponent implements OnInit {

  t: Target = new Target();
   typeopt:String[]=["Local folder","Network share","S3","SFTP"];

  constructor(private app:AppService , private targetService: TargetService, private router:Router) {}

  ngOnInit(): void {
  }

  saveTarget(){
    this.targetService.createTarget(this.t).subscribe(data =>{
      console.log(data);
      this.gotoTargetList();
    },error=>console.log(error));
}

gotoTargetList(){
  this.router.navigate(['/targets']);

}

onSubmit(){
  console.log(this.t);
    this.saveTarget();
}
authenticated(){return this.app.authenticated};

}
