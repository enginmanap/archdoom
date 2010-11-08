from django.db import models
from django.contrib.auth.models import User

class Member(models.Model):
    user = models.ForeignKey(User, unique=True)
    birthDate = models.DateTimeField("dogum_tarihi")
    studentNo = models.IntegerField(verbose_name = "ogrenci_numarasi")
    studentClass = models.IntegerField(verbose_name = "ogrenci_sinifi")
    
    