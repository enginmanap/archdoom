# Create your views here.
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from bmforum.member.forms import MemberForm, PrivateMessageForm
from django.contrib.auth.models import User, Group
from bmforum.member.models import Member, PrivateMessage
from bmforum.forum.models import Entry, Topic
from bmforum.planet.models import Blog
from django.contrib.auth import authenticate, login, logout
from datetime import datetime
from bmforum.ogrenciCalismalari.forms import ProjectForm, ProfessorForm, CourseForm

ogrenciCalismalariTopic = get_object_or_404(Topic, pk=1)

def ogrenciCalismalari(request):
    return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))

def projects(request):
    return render_to_response('ogrenciCalismalari/projects.html', context_instance=RequestContext(request))

def newProject(request):
    if request.POST:
        form = ProjectForm(request.POST)
        if form.is_valid():
            #project = Project()
            project = form.save(commit = False)
            project.year = form.cleaned_data['year']
            project.website = form.cleaned_data['website']
            project.doneBy = form.cleaned_data['doneBy']
            print "proje dolduruldu"

            topic = Topic()
            topic.title = form.cleaned_data['projectName']
            topic.subTopic = ogrenciCalismalariTopic
            topic.date = datetime.now()
            member = get_object_or_404(Member, user = request.user)
            topic.owner = member
            topic.save()
            print "topic kaydedildi"

            project.name = get_object_or_404(Topic, pk=topic.id)

            entry = Entry()
            entry.member = member
            entry.topic = topic
            entry.text = form.cleaned_data['projectDescription']
            entry.date = datetime.now()
            entry.save()
            print "entry kaydedildi"

            project.description = get_object_or_404(Entry, pk=entry.id)

            project.course = form.cleaned_data['course']
            project.save()
            print "project kaydedil"
            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
        else:
            error = "form is not valid"
            return render_to_response('error.html', {'error': error})
    else:
        form = ProjectForm()
        return render_to_response('ogrenciCalismalari/newProject.html', {'form':form,}, context_instance=RequestContext(request))

def newProfessor(request):
    if request.POST:
        form = ProfessorForm(request.POST)
        if form.is_valid():
            professor = form.save(commit = False)
            professor.name = form.cleaned_data['name']
            professor.academicTitle = form.cleaned_data['academicTitle']
            professor.save()

            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
    else:
        form = ProfessorForm()
        return render_to_response('ogrenciCalismalari/newProfessor.html', {'form':form, }, context_instance=RequestContext(request))

def newCourse(request):
    if request.POST:
        form = CourseForm(request.POST)
        if form.is_valid():
            course = form.save(commit = False)
            course.name = form.cleaned_data['name']
            course.term = form.cleaned_data['term']
            course.credits = form.cleaned_data['credits']
            course.explanation = form.cleaned_data['explanation']
            course.courseBy = form.cleaned_data['courseBy']
            course.save()

            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
    else:
        form = CourseForm()
        return render_to_response('ogrenciCalismalari/newCourse.html', {'form':form, }, context_instance=RequestContext(request))
