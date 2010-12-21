from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^planetRegister/$','planet.views.planetRegister', name='planetRegister'),
)
