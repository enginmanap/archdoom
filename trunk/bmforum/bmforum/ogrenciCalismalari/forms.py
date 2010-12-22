#!/usr/bin/python
# -*- coding: utf-8 -*-

from django import forms
from bmforum.ogrenciCalismalari.models import Project, Course, Professor
from django.contrib.auth.models import User

class ProjectForm(forms.ModelForm):
    projectName = forms.CharField(label='Baslik', max_length=30)
    projectDescription = forms.CharField(label='Aciklama', widget=forms.Textarea(attrs={'style':'width: 760px;'}))
    doneBy = forms.CharField(label='Gelistirenler', widget=forms.Textarea(attrs={'style':'width: 760px;'}))

    class Meta:
        model = Project
        fields = ('year', 'website', 'doneBy', 'course', 'projectName', 'projectDescription', )

class CourseForm(forms.ModelForm):
    class Meta:
        model = Course
        fields = ('name', 'term', 'credits', 'explanation', 'courseBy', )

class ProfessorForm(forms.ModelForm):
    class Meta:
        model = Professor
