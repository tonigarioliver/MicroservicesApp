import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegiserFormComponent } from './regiser-form.component';

describe('RegiserFormComponent', () => {
  let component: RegiserFormComponent;
  let fixture: ComponentFixture<RegiserFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegiserFormComponent]
    });
    fixture = TestBed.createComponent(RegiserFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
