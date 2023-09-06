import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultmessageComponent } from './defaultmessage.component';

describe('DefaultmessageComponent', () => {
  let component: DefaultmessageComponent;
  let fixture: ComponentFixture<DefaultmessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefaultmessageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DefaultmessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
