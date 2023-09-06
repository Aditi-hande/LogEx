import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateMaskComponent } from './create-mask/create-mask.component';
import { CreateQueryComponent } from './create-query/create-query.component';
import { CreateScheduleComponent } from './create-schedule/create-schedule.component';
import { CreateTargetComponent } from './create-target/create-target.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MaskListComponent } from './mask-list/mask-list.component';
import { QueryListComponent } from './query-list/query-list.component';
import { QueryResultComponent } from './query-result/query-result.component';
import { ScheduleListComponent } from './schedule-list/schedule-list.component';
import { TargetListComponent } from './target-list/target-list.component';
import { UpdateMaskComponent } from './update-mask/update-mask.component';
import { UpdateQueryComponent } from './update-query/update-query.component';
import { UpdateScheduleComponent } from './update-schedule/update-schedule.component';
import { UpdateTargetComponent } from './update-target/update-target.component';

const routes: Routes = [
  {path:'',redirectTo:'home', pathMatch:'full'},
  {path:'home',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'queries', component:QueryListComponent}, //js object for routes
  {path:'create-query', component: CreateQueryComponent},
  {path:'update-query/:queryName', component: UpdateQueryComponent},
  {path:'masks', component:MaskListComponent},
  {path:'create-mask', component:CreateMaskComponent},
  {path:'update-mask/:maskName', component:UpdateMaskComponent},
  {path:'targets',component:TargetListComponent},
  {path:'create-target', component:CreateTargetComponent},
  {path:'update-target/:targetName', component:UpdateTargetComponent},
  {path:'query-result/:queryName',component:QueryResultComponent},
  {path:'schedules',component:ScheduleListComponent},
  {path:'create-schedule', component: CreateScheduleComponent},
  {path:'update-schedule/:scheduleName', component: UpdateScheduleComponent}
  


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
