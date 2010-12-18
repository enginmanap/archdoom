# Create your views here.
from django.shortcuts import render_to_response
from django.http import HttpResponse
from bmforum.forum.models import *

def topic(request):
    entry_list = Entry.objects.all()
    return render_to_response('forum/topicentry.html', {'entry_list': entry_list})
