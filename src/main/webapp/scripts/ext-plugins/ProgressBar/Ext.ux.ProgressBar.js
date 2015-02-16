Ext.namespace('Ext.ux');

/**
 * Progress bar component.
 *
 * @author Maxim Bazhenov
 */
Ext.ux.ProgressBar = function(config)
{
  Ext.ux.ProgressBar.superclass.constructor.call(this, config);
}

Ext.extend(Ext.ux.ProgressBar, Ext.BoxComponent, {

  cls: 'x-ux-progressbar',

  line_el: null,
  
  text_el: null,
  
  text: true,
  
  value: null,
  
  min: 0,
  
  max: 100,
  
  initComponent : function()
  {
    if (this.value === null) {
      this.value = this.min;
    }
  },
  
  getMin : function()
  {
    return this.min;
  },
  
  setMin : function(min)
  {
    this.min = min;
  },
  
  getMax : function()
  {
    return this.max;
  },
  
  setMax : function(max)
  {
    this.max = max;
  },

  getValue : function(value)
  {
    return this.value;
  },
  
  getPercentValue : function(value)
  {
    value = value || this.value;
    var gap = this.max - this.min;
    value -= this.min;
    return gap != 0 ? Math.round(value * 100 / gap) : 0;
  },
  
  setValue : function(value, set_text)
  {
    this.value = value;
    if (set_text === true) {
      this.setText(this.getPercentValue() + '%');
    }
    else if (typeof(set_text) == 'string') {
      this.setText(set_text);
    }
    this.refresh();
  },
  
  setText : function(text)
  {
    this.text = text;
    this.refresh();
  },
  
  refresh : function()
  {
    if (!this.disabled) {
      if (this.text_el) {
        this.text_el.dom.innerHTML = this.text;
      }
      if (this.line_el) {
        this.line_el.setStyle('width', this.getPercentValue() + '%');
      }
    }
  },

  onRender : function(ct, position)
  {
    if (!this.el) {
      this.el = Ext.DomHelper.overwrite(
        ct, 
        {
          id: this.getId(),
          tag: 'div',
          style: 'position: relative',
          children: [
            {
              id: this.getId() + '-line',
              tag: 'div',
              'class': 'x-ux-progressbar-line'
            },
            {
              id: this.getId() + '-text',
              tag: 'div',
              'class': 'x-ux-progressbar-text',
              style: 'position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; width: 100%',
              html: '&#160'
            }
          ]
        },
        true
      );
      this.line_el = this.el.child('div:first-child');
      this.text_el = this.el.child('div:last-child');
      //if (Ext.isIE) {
      //  this.text_el.setStyle('width', '100%');
      //}
      this.setValue(this.value, this.text);
      this.refresh();
    }
  },
  
  adjustLineHeight : function()
  {
    if (this.text_el) {
      this.text_el.setStyle('line-height', this.el.getHeight(true) + 'px');
    }
  },
  
  afterRender : function(ct)
  {
    this.adjustLineHeight();
  },
  
  onResize : function(adjWidth, adjHeight, rawWidth, rawHeight)
  {
	 this.adjustLineHeight();
  }
});

/**
 * Toolbar progress bar item.
 * 
 * @author Maxim Bazhenov
 */
Ext.ux.ProgressBarItem = function(config)
{
  this.progress = new Ext.ux.ProgressBar(config);
  Ext.ux.ProgressBarItem.superclass.constructor.call(this, null);
}

Ext.extend(Ext.ux.ProgressBarItem, Ext.Toolbar.Item, {
  
  progress: null,
  
  getProgressBar: function()
  {
    return this.progress;
  },
  
  render : function(td)
  {
    this.td = td;
    this.el = this.progress.render(td).getEl().dom;
  },
  
  disable : function()
  {
    this.progress.disable();
    Ext.ux.ProgressBarItem.superclass.disable.call(this);
  },
  
  enable : function()
  {
    this.progress.enable();
    Ext.ux.ProgressBarItem.superclass.enable.call(this);    
  }
});