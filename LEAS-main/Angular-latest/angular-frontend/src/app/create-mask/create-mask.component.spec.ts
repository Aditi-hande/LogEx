import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMaskComponent } from './create-mask.component';

describe('CreateMaskComponent', () => {
  let component: CreateMaskComponent;
  let fixture: ComponentFixture<CreateMaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
