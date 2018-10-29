package com.dughettidanilo.ide.cryptostats.parts;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.dughettidanilo.ide.cryptostats.model.CryptoPrice;
import com.dughettidanilo.ide.cryptostats.networking.HttpRequester;


import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;

public class CryptoStatsView {
	private Label myLabelInView;
	private CryptoPrice cryptoPrice;

	@PostConstruct
	public void createPartControl(Composite parent) throws IOException {
		parent.layout(true, true);			
		
		GridLayout layout = new GridLayout(4, false);
		parent.setLayout(layout);

		Button refreshButton = new Button(parent, SWT.PUSH);
		refreshButton.setText("refresh");
		refreshButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
		refreshButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				 switch (arg0.type) {
			        case SWT.Selection:
			        try {
						refreshCryptoPrices();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        break;
			        }
				
			}
		});
		myLabelInView = new Label(parent, SWT.COLOR_DARK_GRAY);
		this.refreshCryptoPrices();
		myLabelInView.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
	
	}
	public void refreshCryptoPrices() throws IOException {
		this.cryptoPrice = HttpRequester.getCryptoPrice("CV");
		myLabelInView.setText(this.cryptoPrice.toString());
	}

	
    public static final String ID = "de.vogella.rcp.intro.forms.view";

	@Focus
	public void setFocus() {
		myLabelInView.setFocus();

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s==null || s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1)
				setSelection(iss.getFirstElement());
			else
				setSelection(iss.toArray());
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("Current single selection class is : " + o.getClass());
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)
		if (myLabelInView != null)
			myLabelInView.setText("This is a multiple selection of " + selectedObjects.length + " objects");
	}
}
