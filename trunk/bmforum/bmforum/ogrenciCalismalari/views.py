# Create your views here.
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from bmforum.member.forms import MemberForm, PrivateMessageForm
from django.contrib.auth.models import User, Group
from bmforum.member.models import Member, PrivateMessage
from bmforum.forum.models import Entry, Topic, TopicPriorities
from bmforum.planet.models import Blog
from django.contrib.auth import authenticate, login, logout
from datetime import datetime
from bmforum.ogrenciCalismalari.models import Extra
import os
from bmforum.ogrenciCalismalari.forms import ProjectForm, ProfessorForm, CourseForm, ExamForm, LectureNoteForm, ExtraForm

ogrenciCalismalariTopic = get_object_or_404(Topic, pk=1)

def ogrenciCalismalari(request):
    return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))

def projects(request):
    return render_to_response('ogrenciCalismalari/projects.html', context_instance=RequestContext(request))

def newExam(request):
    if request.POST:
        form = ExamForm(request.POST, prefix ='examForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        if form.is_valid():
            if extraForm.is_valid():
                extra = extraForm.save(commit = False)
                extra.file = extraForm.cleaned_data['file']
                extra.description = extraForm.cleaned_data['description']
                extra.fileName =  os.path.split(extra.file.name)[1:]
                extra.save()
            #project = Project()
            exam = form.save(commit = False)
            exam.year = form.cleaned_data['year']

            topic = Topic()
            topic.title = form.cleaned_data['examName']
            topic.subTopic = ogrenciCalismalariTopic
            topic.date = datetime.now()
            member = get_object_or_404(Member, user = request.user)
            topic.owner = member
            topic.priority = get_object_or_404(TopicPriorities, pk=1)
            topic.save()

            exam.name = get_object_or_404(Topic, pk=topic.id)

            entry = Entry()
            entry.member = member
            entry.topic = topic
            entry.text = form.cleaned_data['examDescription']
            entry.date = datetime.now()
            entry.save()
            topic.firstEntry = get_object_or_404(Entry, pk=entry.id)
            topic.save()
            exam.description = get_object_or_404(Entry, pk=entry.id)

            exam.course = form.cleaned_data['course']
            exam.save()
            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
        else:
            print form.errors
            error = "form is not valid"
            return render_to_response('error.html', {'error': error})
    else:
        form = ExamForm(prefix ='examForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        return render_to_response('ogrenciCalismalari/newExam.html', {'form':form, 'extraForm':extraForm}, context_instance=RequestContext(request))

def newLectureNote(request):
    if request.POST:
        form = LectureNoteForm(request.POST, prefix ='lectureNoteForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        if form.is_valid():
            if extraForm.is_valid():
                extra = extraForm.save(commit = False)
                extra.file = extraForm.cleaned_data['file']
                extra.description = extraForm.cleaned_data['description']
                extra.fileName =  os.path.split(extra.file.name)[1:]
                extra.save()
            lectureNote = form.save(commit = False)
            lectureNote.year = form.cleaned_data['year']

            topic = Topic()
            topic.title = form.cleaned_data['lectureNoteName']
            topic.subTopic = ogrenciCalismalariTopic
            topic.date = datetime.now()
            member = get_object_or_404(Member, user = request.user)
            topic.owner = member
            topic.priority = get_object_or_404(TopicPriorities, pk=1)
            topic.save()

            lectureNote.name = get_object_or_404(Topic, pk=topic.id)

            entry = Entry()
            entry.member = member
            entry.topic = topic
            entry.text = form.cleaned_data['lectureNoteDescription']
            entry.date = datetime.now()
            entry.save()
            topic.firstEntry = get_object_or_404(Entry, pk=entry.id)
            topic.save()
            lectureNote.description = get_object_or_404(Entry, pk=entry.id)

            lectureNote.course = form.cleaned_data['course']
            lectureNote.save()
            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
        else:
            print form.errors
            error = "form is not valid"
            return render_to_response('error.html', {'error': error})
    else:
        form = LectureNoteForm(prefix ='lectureNoteForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        return render_to_response('ogrenciCalismalari/newLectureNote.html', {'form':form, 'extraForm':extraForm}, context_instance=RequestContext(request))
    
    
def newProject(request):
    if request.POST:
        form = ProjectForm(request.POST, prefix ='projectForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        if form.is_valid():
            if extraForm.is_valid():
                extra = extraForm.save(commit = False)
                extra.file = extraForm.cleaned_data['file']
                extra.description = extraForm.cleaned_data['description']
                extra.fileName =  os.path.split(extra.file.name)[1:]
                extra.save()
            #project = Project()
            project = form.save(commit = False)
            project.year = form.cleaned_data['year']
            project.website = form.cleaned_data['website']
            project.doneBy = form.cleaned_data['doneBy']

            topic = Topic()
            topic.title = form.cleaned_data['projectName']
            topic.subTopic = ogrenciCalismalariTopic
            topic.date = datetime.now()
            member = get_object_or_404(Member, user = request.user)
            topic.owner = member
            topic.priority = get_object_or_404(TopicPriorities, pk=1)
            topic.save()

            project.name = get_object_or_404(Topic, pk=topic.id)

            entry = Entry()
            entry.member = member
            entry.topic = topic
            entry.text = form.cleaned_data['projectDescription']
            entry.date = datetime.now()
            entry.save()
            topic.firstEntry = get_object_or_404(Entry, pk=entry.id)
            topic.save()
            project.description = get_object_or_404(Entry, pk=entry.id)

            project.course = form.cleaned_data['course']
            project.save()
            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
        else:
            print form.errors
            error = "form is not valid"
            return render_to_response('error.html', {'error': error})
    else:
        form = ProjectForm(prefix ='projectForm')
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        return render_to_response('ogrenciCalismalari/newProject.html', {'form':form, 'extraForm':extraForm}, context_instance=RequestContext(request))

def newExtra(request):
    if request.POST:
        extraForm = ExtraForm(request.POST, prefix ='extraForm')
        if extraForm.is_valid():
            extra = extraForm.save(commit = False)
            extra.file = extraForm.cleaned_data['file']
            extra.description = extraForm.cleaned_data['description']
            extra.fileName =  os.path.split(extra.file.name)[1:]
            extra.save()
            return render_to_response('ogrenciCalismalari/ogrenciCalismalari.html', context_instance=RequestContext(request))
        else:
            print extraForm.errors
            error = "extraForm is not valid"
            return render_to_response('error.html', {'error': error})
    else:
        extraForm = ExtraForm(prefix ='extraForm')
        return render_to_response('ogrenciCalismalari/newExtra.html', {'extraForm':extraForm,}, context_instance=RequestContext(request))

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
