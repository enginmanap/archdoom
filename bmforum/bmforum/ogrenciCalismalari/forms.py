#!/usr/bin/python
# -*- coding: utf-8 -*-
from django import forms
from bmforum.ogrenciCalismalari.models import Project, Course, Professor, Exam, Extra,\
    LectureNote
from django.contrib.auth.models import User

class ExamForm(forms.ModelForm):
    examName = forms.CharField(label='Baslik', max_length=30)
    examDescription = forms.CharField(label='Aciklama', widget=forms.Textarea(attrs={'style':'width: 760px;'}))
    class Meta:
        model = Exam
        fields = ('examName', 'examDescription', 'year', 'course', )

class LectureNoteForm(forms.ModelForm):
    lectureNoteName = forms.CharField(label='Baslik', max_length=30)
    lectureNoteDescription = forms.CharField(label='Aciklama', widget=forms.Textarea(attrs={'style':'width: 760px;'}))
    class Meta:
        model = LectureNote
        fields = ('lectureNoteName', 'lectureNoteDescription', 'year', 'course', )

class ProjectForm(forms.ModelForm):
    projectName = forms.CharField(label='Baslik', max_length=30)
    projectDescription = forms.CharField(label='Aciklama', widget=forms.Textarea(attrs={'style':'width: 760px;'}))
    doneBy = forms.CharField(label='Gelistirenler', widget=forms.Textarea(attrs={'style':'width: 760px;'}))

    class Meta:
        model = Project
        fields = ('projectName', 'projectDescription', 'year', 'website', 'doneBy', 'course', )

class CourseForm(forms.ModelForm):
    class Meta:
        model = Course
        fields = ('name', 'term', 'credits', 'explanation', 'courseBy', )

class ProfessorForm(forms.ModelForm):
    class Meta:
        model = Professor

class ExtraForm(forms.ModelForm):
    description = forms.CharField(label='Dosya Aciklamasi', widget=forms.Textarea(attrs={'style':'width: 400px;'}))
    class Meta:
        model = Extra
        fields = ('file','description')