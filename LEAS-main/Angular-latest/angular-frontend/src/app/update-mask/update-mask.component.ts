import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from '../app.service';
import { Mask } from '../mask';
import { MaskService } from '../mask.service';

@Component({
  selector: 'app-update-mask',
  templateUrl: './update-mask.component.html',
  styleUrls: ['./update-mask.component.css']
})
export class UpdateMaskComponent implements OnInit {

  m: Mask = new Mask();
  maskName: string;
   
  constructor(
    private app:AppService,
    private maskService: MaskService,
              private route: ActivatedRoute,
              private router: Router ) { }

  ngOnInit(): void {
    this.maskName=this.route.snapshot.params['maskName'];
    this.maskService.getMaskByName(this.maskName).subscribe(data=>{
      this.m=data;
    },error=> console.log(error));
  }

  gotoMaskList(){
    this.router.navigate(['/masks']);
  
  }

  onCancel(){
   this.gotoMaskList();
  }

  onSubmit(){
    this.maskService.updateMask(this.maskName,this.m).subscribe(data=>{
      this.gotoMaskList();
    },error=> console.log(error));
  }
  authenticated() {return this.app.authenticated; }

}
