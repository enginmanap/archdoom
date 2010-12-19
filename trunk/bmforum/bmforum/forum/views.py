# Create your views here.
from django.shortcuts import render_to_response, get_object_or_404
from django.http import HttpResponse
from django.core.context_processors import csrf
from bmforum.forum.models import *
from bmforum.forum.forms import EntryForm
from django.template import RequestContext
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.contrib.auth import authenticate, login

def giris(request):
    return render_to_response('base.html')

def topicList(request):
    topic_list = Topic.objects.all()
    return render_to_response('forum/topiclist.html', {'topic_list':topic_list})


def showTopic(request, topic_id, topic_name):
    if request.POST:
        form = EntryForm(request.POST)
        if form.is_valid():
            entry = form.save(commit = False)
    else:
        topic = get_object_or_404(Topic, pk = topic_id)
        entry_list = Entry.objects.filter(topic = topic)
        form = EntryForm()
        return render_to_response('forum/topicentry.html', {'entry_list': entry_list, 'topic':topic, "form":form,}, context_instance=RequestContext(request))


def auth(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
            login(request, user)
            return HttpResponseRedirect(reverse('bmforum.forum.views.topiclist'))
        else:
            # Return a 'disabled account' error message
            return HttpResponseRedirect(reverse('bmforum.forum.views.topiclist'))
    else:
        # Return an 'invalid login' error message.
        return HttpResponseRedirect(reverse('bmforum.forum.views.topiclist'))
        