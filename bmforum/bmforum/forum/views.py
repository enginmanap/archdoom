# Create your views here.
from django.shortcuts import render_to_response, get_object_or_404
from django.http import HttpResponse
from bmforum.forum.models import *

def giris(request):
    return render_to_response('base.html')

def topicList(self):
    topic_list = Topic.objects.all()
    return render_to_response('forum/topiclist.html', {'topic_list':topic_list})

def showTopic(request, topic_id, topic_name):
    topic = get_object_or_404(Topic, pk = topic_id)
    entry_list = Entry.objects.filter(topic = topic)
    return render_to_response('forum/topicentry.html', {'entry_list': entry_list, 'topic':topic })
