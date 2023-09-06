import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Mask } from '../mask';
import { MaskService } from '../mask.service';
import {AppService} from '../app.service';
@Component({
  selector: 'app-create-mask',
  templateUrl: './create-mask.component.html',
  styleUrls: ['./create-mask.component.css']
})
export class CreateMaskComponent implements OnInit {

  m: Mask = new Mask();
   
  constructor(private app:AppService,private maskService: MaskService,private router:Router) { }

  ngOnInit(): void {
    
   
    
  }
  saveMask(){
    this.maskService.createMask(this.m).subscribe(data =>{
      console.log(data);
      this.gotoMaskList();
    },error=>console.log(error));
}
 
gotoMaskList(){
  this.router.navigate(['/masks']);

}

onSubmit(){
      console.log(this.m);
        this.saveMask();
  }
  authenticated(){return this.app.authenticated};
}
