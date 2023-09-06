import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
import { Target } from '../target';
import { TargetService } from '../target.service';

@Component({
  selector: 'app-target-list',
  templateUrl: './target-list.component.html',
  styleUrls: ['./target-list.component.css']
})
export class TargetListComponent implements OnInit {

  targets: Target[];
  
  
  constructor(
    private app:AppService,
    private targetService: TargetService, private router: Router) { } //injected target service 

  ngOnInit(): void {
    this.getTargets();
  }
  private getTargets(){
    this.targetService.getTargetList().subscribe(data => {this.targets = data;}) //async calls
  }
   gotoCreateTarget(){
    this.router.navigate(['/create-target']);

  }
  updateTarget(targetName: String){
    this.router.navigate(['update-target',targetName]);
  }
  deleteTarget(targetName:String){
    this.targetService.deleteTarget(targetName).subscribe(data =>{
      this.getTargets();
    })
  }
  authenticated() {return this.app.authenticated; }


}
