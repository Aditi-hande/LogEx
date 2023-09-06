import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { QueryListComponent } from './query-list/query-list.component';
import { CreateQueryComponent } from './create-query/create-query.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { UpdateQueryComponent } from './update-query/update-query.component';
import { MaskListComponent } from './mask-list/mask-list.component';
import { CreateMaskComponent } from './create-mask/create-mask.component';
import { UpdateMaskComponent } from './update-mask/update-mask.component';
import { HomeComponent } from './home/home.component';
import { TargetListComponent } from './target-list/target-list.component';
import { CreateTargetComponent } from './create-target/create-target.component';
import { UpdateTargetComponent } from './update-target/update-target.component';
import { QueryResultComponent } from './query-result/query-result.component';
import { CreateScheduleComponent } from './create-schedule/create-schedule.component';
import { ScheduleListComponent } from './schedule-list/schedule-list.component';
import { UpdateScheduleComponent } from './update-schedule/update-schedule.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BsDropdownModule,BsDropdownConfig } from 'ngx-bootstrap/dropdown';
import { PaginationModule,PaginationConfig } from 'ngx-bootstrap/pagination';
import { PopoverModule, PopoverConfig } from 'ngx-bootstrap/popover';
import { ProgressbarModule,ProgressbarConfig } from 'ngx-bootstrap/progressbar';
import { RatingModule, RatingConfig } from 'ngx-bootstrap/rating';
import { SortableModule, DraggableItemService } from 'ngx-bootstrap/sortable';
import { TabsModule, TabsetConfig } from 'ngx-bootstrap/tabs';
import { TimepickerModule } from 'ngx-bootstrap/timepicker';
import { BsDatepickerModule, BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { AlertModule,AlertConfig } from 'ngx-bootstrap/alert';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSliderModule} from '@angular/material/slider';
import { LoginComponent } from './login/login.component';
import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS
} from '@angular/common/http';
import { DefaultmessageComponent } from './defaultmessage/defaultmessage.component';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let currUser  =localStorage.getItem('currUser')
    if(currUser){
      const xhr = req.clone({
        setHeaders:{
          Authorization:currUser,
        }
      });
      return next.handle(xhr);

    }
    else{
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
  }
}

@NgModule({
  declarations: [
    AppComponent,
    QueryListComponent,
    CreateQueryComponent,
    NavBarComponent,
    UpdateQueryComponent,
    MaskListComponent,
    CreateMaskComponent,
    UpdateMaskComponent,
    HomeComponent,
    TargetListComponent,
    CreateTargetComponent,
    UpdateTargetComponent,
    QueryResultComponent,
    CreateScheduleComponent,
    ScheduleListComponent,
    UpdateScheduleComponent,
    LoginComponent,
    DefaultmessageComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRadioModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSliderModule,
    MatSlideToggleModule,
    AccordionModule,
    AlertModule,
    ButtonsModule,
    CarouselModule,
    CollapseModule,
    BsDatepickerModule.forRoot(),
    BsDropdownModule,
    PaginationModule,
    PopoverModule,
    ProgressbarModule,
    RatingModule,
    SortableModule,
    TabsModule,
    TimepickerModule.forRoot(),
      

  ],
  providers: [AlertConfig, 
    BsDatepickerConfig, 
    BsDropdownConfig,
    PaginationConfig,
    ProgressbarConfig,
    RatingConfig,
    DraggableItemService,
    TabsetConfig,
    AppService,
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
  bootstrap: [AppComponent],
})
export class AppModule { }
