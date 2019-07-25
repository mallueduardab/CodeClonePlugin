package plugin.views;

import org.eclipse.swt.graphics.Color;



import java.util.ArrayList;



import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import plugin.handlers.SampleHandler;
import plugin.persistences.StatusConversa;

public class SampleView extends ViewPart {

	public static final String ID = "tp1.views.SampleView";

	private TableViewer viewer;
	private Action doubleClickAction;
	private Action applyRemodularizationAction;

	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(4, false);
		parent.setLayout(layout);
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		String[] titles = { " Pacote ", " depende de " };
		int[] bounds = { 150, 150, 150, 150 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StatusConversa r = (StatusConversa) element;
				return r.getPacoteA();
			}

			public Color getBackground(Object element) {
				StatusConversa r = (StatusConversa) element;
				if (r.getTipoDependencia() == 4) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				} else if (r.getTipoDependencia() == 2) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
				} else if (r.getTipoDependencia() == 3) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
				} else {
					return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
				}
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StatusConversa r = (StatusConversa) element;
				return r.getPacoteB();
			}

			public Color getBackground(Object element) {
				StatusConversa r = (StatusConversa) element;
				if (r.getTipoDependencia() == 4) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				} else if (r.getTipoDependencia() == 2) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
				} else if (r.getTipoDependencia() == 3) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
				} else {
					return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
				}
			}
		});

		viewer.refresh();

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());

		viewer.setInput(SampleHandler.recomendacoes);
		getSite().setSelectionProvider(viewer);
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		makeActions();
		hookContextMenu();
		contributeToActionBars();
		hookDoubleClickAction();

	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});

		doubleClickAction = new Action() {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				StatusConversa r = (StatusConversa) selection.getFirstElement();

				StringBuilder sb = new StringBuilder();
				ArrayList<String> aux = new ArrayList<String>();
				for (int i = 0; i < SampleHandler.status.size(); i++) {
					if (SampleHandler.status.get(i).getPacoteA().equals(r.getPacoteA())
							&& SampleHandler.status.get(i).getPacoteB().equals(r.getPacoteB())
							&& (!aux.contains(SampleHandler.status.get(i).getClasseA()))) {

						if (r.getTipoDependencia() == 3) {
							sb.append("A classe " + SampleHandler.status.get(i).getClasseA() + "."
									+ SampleHandler.status.get(i).getPacoteA());
							sb.append(" PODE MAS NAO depdende de ");
							sb.append(r.getPacoteB() + "\n");
							aux.add(SampleHandler.status.get(i).getClasseA());
						} else if (r.getTipoDependencia() == 4) {
							sb.append("A classe " + SampleHandler.status.get(i).getClasseA() + "."
									+ SampleHandler.status.get(i).getPacoteA());
							sb.append(" DEVE MAS NAO depdende de ");
							sb.append(r.getPacoteB() + "\n");
							aux.add(SampleHandler.status.get(i).getClasseA());
						} else if (r.getTipoDependencia() == 2) {
							sb.append("A classe " + SampleHandler.status.get(i).getClasseA() + "."
									+ SampleHandler.status.get(i).getPacoteA());
							sb.append(" NAO PODE MAS depdende de ");
							sb.append(r.getPacoteB() + "\n");
							aux.add(SampleHandler.status.get(i).getClasseA());
						}
					}
				}
				MessageDialog.openInformation(HandlerUtil.getActiveShell(SampleHandler.event),
						"Informa��o de conformidade", sb.toString());

				IWorkbenchPage wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				// Find desired view :
				IViewPart myView = wp.findView("tp1.views.SampleView");

				// Hide the view :
				wp.hideView(myView);

				try {
					wp.showView("tp1.views.SampleView");
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(applyRemodularizationAction);
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(applyRemodularizationAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(applyRemodularizationAction);

	}

	private void makeActions() {
		applyRemodularizationAction = new Action() {
			public void run() {

			}

		};

		applyRemodularizationAction.setToolTipText("Apply Remodularization");
		applyRemodularizationAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		applyRemodularizationAction.setEnabled(true);
	}

}