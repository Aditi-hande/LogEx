import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from '../app.service';
import { Target } from '../target';
import { TargetService } from '../target.service';

@Component({
  selector: 'app-update-target',
  templateUrl: './update-target.component.html',
  styleUrls: ['./update-target.component.css']
})
export class UpdateTargetComponent implements OnInit {

  t :Target= new Target();
  targetName:String;
  typeopt:String[]=["Local folder","Network share","S3","SFTP"];

  constructor(
    private app:AppService,
    private targetService: TargetService, private router:Router, private route: ActivatedRoute,) { }

  ngOnInit(): void {
    this.targetName=this.route.snapshot.params['targetName'];
    this.targetService.getTargetByName(this.targetName).subscribe(data=>{
      this.t=data;
    },error=> console.log(error));
  }

  gotoTargetList(){
    this.router.navigate(['/targets']);
  
  }

  onCancel(){
    this.gotoTargetList();
  }

  onSubmit(){
    this.targetService.updateTarget(this.targetName,this.t).subscribe(data=>{
      this.gotoTargetList();
    },error=> console.log(error));
  }
  authenticated() {return this.app.authenticated; }


}
