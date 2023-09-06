import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
import { Mask } from '../mask';
import { MaskService } from '../mask.service';

@Component({
  selector: 'app-mask-list',
  templateUrl: './mask-list.component.html',
  styleUrls: ['./mask-list.component.css']
})
export class MaskListComponent implements OnInit {

  masks: Mask[];
  
  
  constructor(
    private app:AppService,
    private maskService: MaskService, private router: Router) { } //injected mask service 

  ngOnInit(): void {
    this.getMasks();
  }
  private getMasks(){
    this.maskService.getMaskList().subscribe(data => {this.masks = data;}) //async calls
  }
   gotoCreateMask(){
    this.router.navigate(['/create-mask']);

  }
  updateMask(maskName: String){
    this.router.navigate(['update-mask',maskName]);
  }
  deleteMask(maskName:String){
    this.maskService.deleteMask(maskName).subscribe(data =>{
      this.getMasks();
    })
  }
  authenticated(){return this.app.authenticated};

}
