from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^projects/$','ogrenciCalismalari.views.projects', name='projects'),
    url(r'^projectAddEntry-(?P<project_id>\d)/$','ogrenciCalismalari.views.projectAddEntry', name='projectAddEntry'),
    url(r'^showProject-(?P<project_id>\d+)/$','ogrenciCalismalari.views.showProject', name='showProject'),
    url(r'^showExam-(?P<exam_id>\d+)/$','ogrenciCalismalari.views.showExam', name='showExam'),
    url(r'^showLectureNote-(?P<lectureNote_id>\d+)/$','ogrenciCalismalari.views.showLectureNote', name='showLectureNote'),
    url(r'^newProject/$','ogrenciCalismalari.views.newProject', name='newProject'),
    url(r'^newProfessor/$','ogrenciCalismalari.views.newProfessor', name='newProfessor'),
    url(r'^newCourse/$','ogrenciCalismalari.views.newCourse', name='newCourse'),
    url(r'^newExam/$','ogrenciCalismalari.views.newExam', name='newExam'),
    url(r'^newLectureNote/$','ogrenciCalismalari.views.newLectureNote', name='newLectureNote'),
    url(r'^$','ogrenciCalismalari.views.ogrenciCalismalari', name='ogrenciCalismalari'),
)
