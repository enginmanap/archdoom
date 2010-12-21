from django.db import models
from bmforum.member.models import Member

class Blog(models.Model):
    blogAddress = models.URLField(verbose_name = "blog_adresi")
    isHidden = models.BooleanField(verbose_name = "silindi_mi?", default=False)
    member = models.ForeignKey(Member, related_name = "blog_sahibi")

class BlogEntry(models.Model):
    blog = models.ForeignKey(Blog, related_name = "blog")
    title = models.CharField(max_length = 512, verbose_name = "girdi_basligi")
    text = models.TextField(verbose_name = "girdi_icerigi")
    date = models.DateTimeField(verbose_name = "girdi_tarihi")
