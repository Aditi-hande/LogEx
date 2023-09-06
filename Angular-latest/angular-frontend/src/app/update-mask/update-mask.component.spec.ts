import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateMaskComponent } from './update-mask.component';

describe('UpdateMaskComponent', () => {
  let component: UpdateMaskComponent;
  let fixture: ComponentFixture<UpdateMaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateMaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateMaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
