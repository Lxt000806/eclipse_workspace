package com.house.framework.commons.workflow;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.BaseBpmnXMLConverter;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.converter.export.ActivitiListenerExport;
import org.activiti.bpmn.converter.export.BPMNDIExport;
import org.activiti.bpmn.converter.export.DefinitionsRootExport;
import org.activiti.bpmn.converter.export.MultiInstanceExport;
import org.activiti.bpmn.converter.export.PoolExport;
import org.activiti.bpmn.converter.export.ProcessExport;
import org.activiti.bpmn.converter.export.SignalAndMessageDefinitionExport;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.apache.commons.lang3.StringUtils;

public class WFBpmnXMLConverter extends BpmnXMLConverter {

	public byte[] convertToXML(BpmnModel model, String encoding) {

		try {
			ByteArrayOutputStream outputStream;
			outputStream = new ByteArrayOutputStream();
			XMLOutputFactory xof = XMLOutputFactory.newInstance();
			// wangzhe
			// OutputStreamWriter out = new OutputStreamWriter(outputStream,
			// encoding);
			// XMLStreamWriter writer = xof.createXMLStreamWriter(out);
			XMLStreamWriter writer = xof.createXMLStreamWriter(outputStream,
					encoding);
			// wangzhe end
			XMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);
			DefinitionsRootExport.writeRootElement(model, xtw, encoding);
			SignalAndMessageDefinitionExport
					.writeSignalsAndMessages(model, xtw);
			PoolExport.writePools(model, xtw);
			Iterator<Process> ip = model.getProcesses().iterator();
			do {
				if (!ip.hasNext())
					break;
				Process process = ip.next();
				if (process.getFlowElements().size() != 0
						|| process.getLanes().size() != 0) {
					ProcessExport.writeProcess(process, xtw);
					FlowElement flowElement;
					Iterator<FlowElement> ita = process.getFlowElements()
							.iterator();
					for (; ita.hasNext();) {
						flowElement = ita.next();
						createXML(flowElement, model, xtw);
					}

					Artifact artifact;
					Iterator<Artifact> itb = process.getArtifacts().iterator();
					for (; itb.hasNext();) {
						artifact = itb.next();
						createXML(artifact, model, xtw);
					}

					xtw.writeEndElement();
				}
			} while (true);

			BPMNDIExport.writeBPMNDI(model, xtw);
			xtw.writeEndElement();
			xtw.writeEndDocument();
			xtw.flush();
			outputStream.close();
			xtw.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			LOGGER.error("Error writing BPMN XML", e);
			throw new XMLException("Error writing BPMN XML", e);
		}
	}

	@SuppressWarnings("rawtypes")
	private void createXML(FlowElement flowElement, BpmnModel model,
			XMLStreamWriter xtw) throws Exception {
		if (flowElement instanceof SubProcess) {
			SubProcess subProcess = (SubProcess) flowElement;
			xtw.writeStartElement("subProcess");
			xtw.writeAttribute("id", subProcess.getId());
			if (StringUtils.isNotEmpty(subProcess.getName()))
				xtw.writeAttribute("name", subProcess.getName());
			else
				xtw.writeAttribute("name", "subProcess");
			if (subProcess instanceof EventSubProcess)
				xtw.writeAttribute("triggeredByEvent", "true");
			if (StringUtils.isNotEmpty(subProcess.getDocumentation())) {
				xtw.writeStartElement("documentation");
				xtw.writeCharacters(subProcess.getDocumentation());
				xtw.writeEndElement();
			}
			boolean wroteListener = ActivitiListenerExport.writeListeners(
					subProcess, false, xtw);
			if (wroteListener)
				xtw.writeEndElement();
			MultiInstanceExport.writeMultiInstance(subProcess, xtw);
			FlowElement subElement;
			Iterator<FlowElement> ie = subProcess.getFlowElements().iterator();
			for (; ie.hasNext();) {
				subElement = ie.next();
				createXML(subElement, model, xtw);
			}
			Artifact artifact;
			Iterator<Artifact> ia = subProcess.getArtifacts().iterator();
			for (; ia.hasNext();) {
				artifact = ia.next();
				createXML(artifact, model, xtw);
			}
			xtw.writeEndElement();
		} else {
			Class converter = (Class) convertersToXMLMap.get(flowElement
					.getClass());
			if (converter == null)
				throw new XMLException((new StringBuilder())
						.append("No converter for ")
						.append(flowElement.getClass()).append(" found")
						.toString());
			((BaseBpmnXMLConverter) converter.newInstance()).convertToXML(xtw,
					flowElement, model);
		}
	}

	@SuppressWarnings("rawtypes")
	private void createXML(Artifact artifact, BpmnModel model,
			XMLStreamWriter xtw) throws Exception {
		Class converter = (Class) convertersToXMLMap.get(artifact.getClass());
		if (converter == null) {
			throw new XMLException((new StringBuilder())
					.append("No converter for ").append(artifact.getClass())
					.append(" found").toString());
		} else {
			((BaseBpmnXMLConverter) converter.newInstance()).convertToXML(xtw,
					artifact, model);
			return;
		}
	}
}
