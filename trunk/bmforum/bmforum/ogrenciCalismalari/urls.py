from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^projects/$','ogrenciCalismalari.views.projects', name='projects'),
    url(r'^newProject/$','ogrenciCalismalari.views.newProject', name='newProject'),
    url(r'^newProfessor/$','ogrenciCalismalari.views.newProfessor', name='newProfessor'),
    url(r'^newCourse/$','ogrenciCalismalari.views.newCourse', name='newCourse'),
    url(r'^$','ogrenciCalismalari.views.ogrenciCalismalari', name='ogrenciCalismalari'),
)
