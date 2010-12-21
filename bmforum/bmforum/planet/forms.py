from django import forms
from bmforum.planet.models import Blog
from django.contrib.auth.models import User

class BlogForm(forms.ModelForm):
    class Meta:
        model = Blog
        fields = {'blogAddress', }
